package hr.ferit.ivanabramusic.lolquiz

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import org.w3c.dom.Text

class HighscoreRecyclerAdapter(val items: ArrayList<Player>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PlayerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int){
        when(holder){
            is PlayerViewHolder -> {
                holder.bind(items[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return this.items.size
    }


    class PlayerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val playerName = itemView.findViewById<TextView>(R.id.playerName)
        private val playerScore = itemView.findViewById<TextView>(R.id.playerScore)


        fun bind(player: Player){
            Glide.with(itemView.context)
            playerName.text =player.name
            playerScore.text = player.score

        }
    }

    fun addItem(player: Player){
        items.add(player)
        items.sortByDescending { it.score.toInt() }
        notifyItemInserted(items.size)

    }

    fun removeAll()
    {
        items.clear()

    }



}