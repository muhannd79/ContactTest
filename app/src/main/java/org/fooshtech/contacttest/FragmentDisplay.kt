package org.fooshtech.contacttest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_display.view.*
import org.fooshtech.contacttest.adapter.ContactsAdapter
import org.fooshtech.contacttest.model.ContactResponse

class FragmentDisplay: Fragment() {

    lateinit var adapter: ContactsAdapter
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        super.onCreateView(inflater, container, savedInstanceState)

        val view = inflater.inflate(R.layout.fragment_display, container, false)

        recyclerView = view.rv_fragment_display
        recyclerView.layoutManager = LinearLayoutManager(activity)

         arguments?.getParcelable<ContactResponse>(EXTRA_DATA_SET)?.let {
            adapter = ContactsAdapter(it)
            recyclerView.adapter = adapter
        }

        return view
    }

    /**
     * Check for the Creation of the view.
     *
     */
    fun passData(dataSet: ContactResponse){
        recyclerView.adapter = ContactsAdapter(dataSet)
    }

    companion object{
        const val EXTRA_DATA_SET = "ExtraDataSet"

        fun newInstance(dataSet: ContactResponse): FragmentDisplay{

            val fragmentDisplay = FragmentDisplay()
            val bundle = Bundle()

            bundle.putParcelable(EXTRA_DATA_SET, dataSet)
            fragmentDisplay.arguments = bundle

            return fragmentDisplay
        }
    }
}