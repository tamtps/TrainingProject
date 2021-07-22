package com.example.trainingproject.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.trainingproject.R
import com.example.trainingproject.screens.CardsActivity

/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
            setSearch(arguments?.getString(ARG_SEARCH_STRING) ?: "Search" )
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_cards, container, false)
        val textView: TextView = root.findViewById(R.id.section_label)
        var searchBar: EditText = requireActivity().findViewById(R.id.search_bar)
        var appbar: TextView = requireActivity().findViewById(R.id.title)

        pageViewModel.text.observe(this, Observer<String> {
            textView.text = it
        })
        pageViewModel.searchText.observe(this, Observer<String> {
            searchBar.hint = it
            appbar.text = it
        })
        return root
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"
        private const val ARG_SEARCH_STRING = "section_string"
        @JvmStatic
        fun newInstance(sectionNumber: Int, search: String): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                    putString(ARG_SEARCH_STRING, search)
                }
            }
        }
    }
}