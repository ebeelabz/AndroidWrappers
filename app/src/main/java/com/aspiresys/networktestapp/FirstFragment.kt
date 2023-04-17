package com.aspiresys.networktestapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.aspiresys.networktestapp.databinding.FragmentFirstBinding
import com.aspiresys.testapp.CustomAdapter

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private var recyclerView: RecyclerView ?= null
    private lateinit var adapter: CustomAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Api Call
        //callApi()

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /*private fun callApi() {
        /*val apiService = RetrofitHelper.getInstance().create(ApiService::class.java)
        // launching a new coroutine
        GlobalScope.launch {
            val result = apiService.getQuotes()
            if (result != null) {
                // Checking the results
                Log.d("## results: ", result.body().toString())
            }
        }*/

        val apiService = RetrofitHelper.getInstance().create(TestApiService::class.java)

        val call: Call<ResponseModel?>? = apiService.getResponse()
        call!!.enqueue(object : Callback<ResponseModel?> {
            override fun onResponse(call: Call<ResponseModel?>?, response: Response<ResponseModel?>) {
                Toast.makeText(activity, "Got response!!", Toast.LENGTH_SHORT).show()
                response.body()?.let {
                    var itemList: List<ItemsItem> = response.body()!!.items as List<ItemsItem>
                    Log.d("Response", "## count: "+itemList.size+", total_count: "+response.body()!!.totalCount+", name: "+ (response.body()!!.items?.get(0)?.name
                        ?: null)+", id: "+response.body()!!.items?.get(0)?.typeId)

                    adapter = CustomAdapter(response.body()!!.items as List<ItemsItem>, activity as Context)
                    val gridLayoutManager = GridLayoutManager(activity,2)
                    recyclerView!!.adapter = adapter
                    recyclerView!!.setHasFixedSize(true)
                    recyclerView!!.layoutManager = gridLayoutManager
                }
            }

            override fun onFailure(call: Call<ResponseModel?>?, t: Throwable?) {
                // displaying an error message in toast
                Toast.makeText(activity, "Fail to get the data..", Toast.LENGTH_SHORT).show()
            }
        })
    }*/
}