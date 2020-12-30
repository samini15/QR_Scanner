package com.shayan.qrnfcscanner.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import butterknife.BindView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.shayan.qrnfcscanner.MainActivity
import com.shayan.qrnfcscanner.R
import com.shayan.qrnfcscanner.adapter.CodeListAdapter
import com.shayan.qrnfcscanner.model.Code
import com.shayan.qrnfcscanner.qrcode.QRCodeDetailFragment
import com.shayan.qrnfcscanner.qrcode.QRCodeDetailFragmentArgs
import com.shayan.qrnfcscanner.utils.Utils
import com.shayan.qrnfcscanner.utils.customUi.FabSmallCustom
import com.shayan.qrnfcscanner.utils.getTransitionNameCompat
import com.shayan.qrnfcscanner.viewModel.CodeListViewModel
import kotlinx.android.synthetic.main.fragment_code_list.*
import kotlinx.android.synthetic.main.view_fab_small.*

class CodeListFragment : Fragment(), CodeListAdapter.ViewHolder.Listener {

    private var viewModel: CodeListViewModel? = null

    private lateinit var adapter: CodeListAdapter

    private var parentActivity: MainActivity? = null

    private val observer = Observer<List<Code>>{
        updateUI(it)
    }


    // region fragment lifecycle

    override fun onAttach(context: Context) {
        super.onAttach(context)

        parentActivity = Utils.safeCast(context, MainActivity::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_code_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initRecyclerView()
        initViewModel()

        fab_small_custom.setOnClickListener { navigateToQRCodeScan() }

        fab.setOnClickListener {
            viewModel?.toggleFabMenu()

            toggleFab()
        }

        this.viewModel?.getCodes()?.observe(viewLifecycleOwner, observer)
    }

    override fun onDetach() {
        super.onDetach()

        parentActivity = null
    }


    // endregion fragment lifecycle

    private fun updateUI(codes: List<Code>) {
        this.adapter.setCodes(codes)

        toggleFab()
    }

    private fun initRecyclerView() {
        recycler_view_code_list.layoutManager = LinearLayoutManager(context)
        this.adapter = CodeListAdapter(mutableListOf(), this)
        recycler_view_code_list.adapter = this.adapter
    }

    private fun initViewModel() {
        this.viewModel = parentActivity?.getViewModel()
    }

    override fun onCodeClick(codeSelected: Code, itemView: View) {

        val iconView = itemView.findViewById<ImageView>(R.id.image_view_icon)
        val urlTextView = itemView.findViewById<TextView>(R.id.text_view_code_url)
        val dateTextView = itemView.findViewById<TextView>(R.id.text_view_date_created)

        // Fragment back stack bug  (LIMIT!!!! of Navigation component), NOT navigation component!
        // https://issuetracker.google.com/issues/118475573

        val extras = FragmentNavigatorExtras(
                urlTextView to urlTextView.getTransitionNameCompat(),
                iconView to iconView.getTransitionNameCompat(),
                dateTextView to dateTextView.getTransitionNameCompat()
        )

        val action = CodeListFragmentDirections.actionCodeListFragmentToQRCodeDetailFragment(codeSelected.id)
        findNavController().navigate(action, extras)

        // We must do the transition manually
        /*val fragment = QRCodeDetailFragment().apply {
            arguments = QRCodeDetailFragmentArgs(codeId = codeSelected.id).toBundle()
        }
        activity?.supportFragmentManager!!
                .beginTransaction()
                .addSharedElement(iconView, iconView.getTransitionNameCompat())
                .addSharedElement(urlTextView, urlTextView.getTransitionNameCompat())
                .addSharedElement(dateTextView, dateTextView.getTransitionNameCompat())
                .addToBackStack(null)
                .replace(R.id.nav_host_fragment_container, fragment)
                .commit()*/
    }

    override fun onCodeLongClick(codeSelected: Code) {
        this.viewModel?.removeCode(codeSelected.id)?.observe(viewLifecycleOwner, observer)
    }

    private fun navigateToQRCodeScan() {
        val action = CodeListFragmentDirections.actionCodeListFragmentToQRCodeScannFragment()
        findNavController().navigate(action)
    }

    // region FAB animations

    private fun toggleFab() {
        if (viewModel?.isFabMenuOpen()!!) {
            openFABMenu()
        } else {
            closeFABMenu()
        }
    }

    private fun openFABMenu() {
        ViewCompat.animate(fab)
                .rotation(45f)
                .setDuration(300)
                .setInterpolator(OvershootInterpolator(10f))
                .start()

        animateShowFab(fab_small_custom)
    }

    private fun closeFABMenu() {
        ViewCompat.animate(fab)
                .rotation(0f)
                .setDuration(300)
                .setInterpolator(OvershootInterpolator(10f))
                .start()

        animateHideFab(fab_small_custom)
    }

    private fun animateShowFab(fab: FabSmallCustom) {
        ViewCompat.animate(fab)
                .translationY(-fab.offsetYAnimation)
                .withStartAction { fab.visibility = View.VISIBLE }
                .withEndAction {
                    fab.labelView.animate()
                            .alpha(1.0f)
                            .duration = 200
                }
    }

    private fun animateHideFab(fab: FabSmallCustom) {
        ViewCompat.animate(fab)
                .translationY(0f)
                .withStartAction { fab.labelView.animate().alpha(0f) }
                .withEndAction { fab.visibility = View.GONE }
    }

    // endregion FAB animation
}