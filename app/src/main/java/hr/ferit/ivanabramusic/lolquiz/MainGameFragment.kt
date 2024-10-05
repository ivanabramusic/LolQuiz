package hr.ferit.ivanabramusic.lolquiz

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import com.google.gson.GsonBuilder
import okhttp3.*

import java.io.IOException


class MainGameFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        val view = inflater.inflate(R.layout.fragment_main_game, container, false)
        var attributes = view.findViewById<TextView>(R.id.champAttributes)
        var releaseDate = view.findViewById<TextView>(R.id.champReleaseDate)
        var resource = view.findViewById<TextView>(R.id.champResource)
        var range = view.findViewById<TextView>(R.id.champRange)
        var blueEssence = view.findViewById<TextView>(R.id.champBlueEssence)
        var hp = view.findViewById<TextView>(R.id.champHP)
        var attDamage = view.findViewById<TextView>(R.id.champAttDamage)
        var score = view.findViewById<TextView>(R.id.scoreNumber)
        var name: String = ""
        val difficulty = arguments?.getString("DIFFICULTY")

        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://league-of-legends-galore.p.rapidapi.com/api/randomChamp")
            .get()
            .addHeader("X-RapidAPI-Key", "d0e54edb59msh39289935d66edf0p1f5e46jsnf81524493ec9")
            .addHeader("X-RapidAPI-Host", "league-of-legends-galore.p.rapidapi.com")
            .build()


        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException)
            {
                e.printStackTrace();
            }

            override fun onResponse(call: Call, response: Response) {
                Log.i("Response","Recived Response from server");
                response.use{
                    if(!response.isSuccessful)
                    {
                        Log.e("HTTP Error", "Something didn't load, or wasn't successful");
                    }
                    else
                    {
                        val body = response.body?.string()
                        val gson = GsonBuilder().create()
                        val champions: List<Champion> = gson.fromJson(body,Array<Champion>::class.java).toList()
                        for (element in champions)
                        {
                            if(difficulty=="EASY") {
                                activity?.runOnUiThread {
                                    attributes.text = "Attributes: ${element.Attributes.toString()}"
                                    releaseDate.text = "Release date: ${element.releaseDate.toString()}"
                                    resource.text = "Resource: ${element.resource.toString()}"
                                    range.text = "Range: ${element.range.toString()}"
                                    blueEssence.text = "Blue essence: ${element.blueEssence.toString()}"
                                    hp.text = "Health: ${element.HP.toString()}"
                                    attDamage.text = "Attack damage: ${element.attDamage.toString()}"
                                    name = element.champName.toString()
                                }
                            }
                            else if(difficulty=="MEDIUM")
                            {
                                activity?.runOnUiThread {
                                    attributes.text = "Attributes: ${element.Attributes.toString()}"
                                    releaseDate.text = "Release date: ${element.releaseDate.toString()}"
                                    resource.text = "Resource: ${element.resource.toString()}"
                                    range.text = "Range: ${element.range.toString()}"
                                    blueEssence.text = "Blue essence: ${element.blueEssence.toString()}"
                                    hp.text = "Health:"
                                    attDamage.text = "Attack damage:"
                                    name = element.champName.toString()
                                }
                            }
                            else if(difficulty=="HARD")
                            {
                                activity?.runOnUiThread {
                                    attributes.text = "Attributes: ${element.Attributes.toString()}"
                                    releaseDate.text = "Release date: ${element.releaseDate.toString()}"
                                    resource.text = "Resource: ${element.resource.toString()}"
                                    range.text = "Range: ${element.range.toString()}"
                                    blueEssence.text = "Blue essence:"
                                    hp.text = "Health:"
                                    attDamage.text = "Attack damage:"
                                    name = element.champName.toString()
                                }
                            }



                        }

                        view.findViewById<Button>(R.id.guessButton).setOnClickListener(){
                            val championGuessed = view.findViewById<TextView>(R.id.enterChampName)

                            if(championGuessed.text.toString() == name)
                            {
                                client.newCall(request).enqueue(object: Callback {
                                    override fun onFailure(call: Call, e: IOException) {
                                        e.printStackTrace();
                                    }

                                    override fun onResponse(call: Call, response: Response) {
                                        Log.i("Response", "Recived Response from server");
                                        response.use {
                                            if (!response.isSuccessful) {
                                                Log.e(
                                                    "HTTP Error",
                                                    "Something didn't load, or wasn't successful"
                                                );
                                            } else {
                                                val body = response.body?.string()
                                                val gson = GsonBuilder().create()
                                                val champions: List<Champion> =
                                                    gson.fromJson(body, Array<Champion>::class.java)
                                                        .toList()
                                                for (element in champions) {
                                                    if (difficulty == "EASY") {
                                                        activity?.runOnUiThread {

                                                            var oldScoreString :String = score.text.toString()
                                                            var oldScoreInt :Int = oldScoreString.toInt()
                                                            oldScoreInt++
                                                            score.text = oldScoreInt.toString()

                                                            attributes.text = "Attributes: ${element.Attributes.toString()}"
                                                            releaseDate.text = "Release date: ${element.releaseDate.toString()}"
                                                            resource.text = "Resource: ${element.resource.toString()}"
                                                            range.text = "Range: ${element.range.toString()}"
                                                            blueEssence.text = "Blue essence: ${element.blueEssence.toString()}"
                                                            hp.text = "Health: ${element.HP.toString()}"
                                                            attDamage.text = "Attack damage: ${element.attDamage.toString()}"
                                                            name = element.champName.toString()
                                                        }
                                                    } else if (difficulty == "MEDIUM") {
                                                        activity?.runOnUiThread {

                                                            var oldScoreString :String = score.text.toString()
                                                            var oldScoreInt :Int = oldScoreString.toInt()
                                                            oldScoreInt += 2
                                                            score.text = oldScoreInt.toString()

                                                            attributes.text = "Attributes: ${element.Attributes.toString()}"
                                                            releaseDate.text = "Release date: ${element.releaseDate.toString()}"
                                                            resource.text = "Resource: ${element.resource.toString()}"
                                                            range.text = "Range: ${element.range.toString()}"
                                                            blueEssence.text = "Blue essence: ${element.blueEssence.toString()}"
                                                            hp.text = "Health:"
                                                            attDamage.text = "Attack damage:"
                                                            name = element.champName.toString()
                                                        }
                                                    } else if (difficulty == "HARD") {
                                                        activity?.runOnUiThread {

                                                            var oldScoreString :String = score.text.toString()
                                                            var oldScoreInt :Int = oldScoreString.toInt()
                                                            oldScoreInt+=3
                                                            score.text = oldScoreInt.toString()

                                                            attributes.text = "Attributes: ${element.Attributes.toString()}"
                                                            releaseDate.text = "Release date: ${element.releaseDate.toString()}"
                                                            resource.text = "Resource: ${element.resource.toString()}"
                                                            range.text = "Range: ${element.range.toString()}"
                                                            blueEssence.text = "Blue essence:"
                                                            hp.text = "Health:"
                                                            attDamage.text = "Attack damage:"
                                                            name = element.champName.toString()
                                                        }
                                                    }
                                                println(name)

                                                }
                                            }
                                        }
                                    }
                                }
                                )
                                championGuessed.text = ""

                            }
                            else {

                                    val secondFragment = GameOverFragment()
                                    val bundle = Bundle()
                                    bundle.putString("SCORE", score.text.toString())
                                    secondFragment.arguments = bundle

                                    val fragmentTransaction: FragmentTransaction? =
                                        activity?.supportFragmentManager?.beginTransaction()
                                    fragmentTransaction?.replace(R.id.fragmentContainerView, secondFragment)
                                    fragmentTransaction?.commit()
                            }
                        }


                    }
                }

            }

        })



        return view
    }



}