package hr.ferit.ivanabramusic.lolquiz

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class HighscoresFragment : Fragment() {

    private val db = Firebase.firestore
    private lateinit var recyclerAdapter: HighscoreRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_highscores, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.highscoresList)
        val deleteButton = view.findViewById<Button>(R.id.deleteButton)
        val backButton = view.findViewById<Button>(R.id.backButton)

        db.collection("players")
            .get()
            .addOnSuccessListener{
                val list: ArrayList<Player> = ArrayList()
                for(data in it.documents)
                {
                    val player = data.toObject(Player::class.java)
                    if(player != null){
                        player.id =data.id
                        list.add(player)
                    }

                }

                list.sortByDescending {it.score.toInt()}
                recyclerAdapter = HighscoreRecyclerAdapter(list)
                recyclerView.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = recyclerAdapter
                }
            }
            .addOnFailureListener{
                Log.e("HighscoresFragment",it.message.toString())
            }


        backButton.setOnClickListener()
        {
            val secondFragment = FrontPageFragment()

            val fragmentTransaction: FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragmentContainerView, secondFragment)
            fragmentTransaction?.commit()
        }



        deleteButton.setOnClickListener()
        {
            val builder = AlertDialog.Builder(getActivity())
            builder.setMessage("Are you sure you want to delete all highscores?")
                .setCancelable(false)
                .setPositiveButton("Yes"){ dialog,id ->
                    recyclerAdapter.removeAll()
                    db.collection("players")
                        .get()
                        .addOnSuccessListener {
                            for(data in it.documents)
                            {
                                db.collection("players").document(data.id).delete()
                            }
                        }
                    val secondFragment = FrontPageFragment()

                    val fragmentTransaction: FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
                    fragmentTransaction?.replace(R.id.fragmentContainerView, secondFragment)
                    fragmentTransaction?.commit()

                    Toast.makeText(activity,"Scores deleted.",Toast.LENGTH_SHORT).show()

                }
                .setNegativeButton("No"){ dialog,id ->
                    dialog.dismiss()
                }
                val alert = builder.create()
                alert.show()


        }



        return view
    }


}