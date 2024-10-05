package hr.ferit.ivanabramusic.lolquiz

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class GameOverFragment : Fragment() {

    private val db = Firebase.firestore
    private lateinit var recyclerAdapter: HighscoreRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_game_over, container, false)
        val saveButton = view.findViewById<Button>(R.id.buttonSave)
        val finalScore = view.findViewById<TextView>(R.id.finalScore)
        finalScore.text = arguments?.getString("SCORE")

        db.collection("players")
            .get()
            .addOnSuccessListener {
                val list: ArrayList<Player> = ArrayList()
                for (data in it.documents) {
                    val player = data.toObject(Player::class.java)
                    if (player != null) {
                        player.id = data.id
                        list.add(player)
                    }

                }

                recyclerAdapter = HighscoreRecyclerAdapter(list)

                saveButton.setOnClickListener()
                {

                    val playerName = view.findViewById<EditText>(R.id.inputName)
                    val secondFragment = FrontPageFragment()

                    if (playerName.text.toString() == "") {
                        val toast = Toast.makeText(activity, "Enter your name!", Toast.LENGTH_SHORT)
                        toast.show()
                    }
                    else
                    {


                        val player = Player(
                            playerName.text.toString() + finalScore.text.toString(),
                            playerName.text.toString(),
                            finalScore.text.toString()
                        )
                        db.collection("players").add(player).addOnSuccessListener {
                            player.id = it.id
                            recyclerAdapter.addItem(player)

                        }

                        Toast.makeText(activity, "Score saved.", Toast.LENGTH_SHORT).show()


                        val fragmentTransaction: FragmentTransaction? =
                            activity?.supportFragmentManager?.beginTransaction()
                        fragmentTransaction?.replace(R.id.fragmentContainerView, secondFragment)
                        fragmentTransaction?.commit()
                    }
                }


            }

        return view
    }

}