package hr.ferit.ivanabramusic.lolquiz

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction

class FrontPageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_front_page, container, false)

        val radioGroup = view.findViewById<RadioGroup>(R.id.difficulties)
        view.findViewById<Button>(R.id.playButton).setOnClickListener()
        {
            val radioButton = view.findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
            if(radioGroup.checkedRadioButtonId ==-1) {

                val toast = Toast.makeText(activity, "Select difficulty!", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.START,200,200)
                toast.show()
            }
            else {
                val secondFragment = MainGameFragment()

                val bundle = Bundle()
                bundle.putString("DIFFICULTY", radioButton.text.toString())
                secondFragment.arguments = bundle

                val fragmentTransaction: FragmentTransaction? =
                    activity?.supportFragmentManager?.beginTransaction()
                fragmentTransaction?.replace(R.id.fragmentContainerView, secondFragment)
                fragmentTransaction?.commit()
            }

        }
        view.findViewById<Button>(R.id.highscoresButton).setOnClickListener()
        {
            val secondFragment = HighscoresFragment()
            val fragmentTransaction: FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragmentContainerView, secondFragment)
            fragmentTransaction?.commit()

        }



        return view
    }


}