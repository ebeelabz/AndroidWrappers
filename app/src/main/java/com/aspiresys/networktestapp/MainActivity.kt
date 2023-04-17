package com.aspiresys.networktestapp

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aspiresys.networklayer.network.core.NetworkBuilder
import com.aspiresys.networklayer.network.core.NetworkListener
import com.aspiresys.networklayer.network.core.NetworkManager
import com.aspiresys.networklayer.network.error.NetworkError
import com.aspiresys.networklayer.network.utils.RequestType
import com.aspiresys.networktestapp.databinding.ActivityMainBinding
import com.aspiresys.testapp.CustomAdapter
import com.aspiresys.testapp.ItemsItem
import com.aspiresys.testapp.ResponseModel
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.JsonElement

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private var recyclerView: RecyclerView?= null
    private lateinit var adapter: CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        recyclerView = findViewById(R.id.recyclerView)

        /*val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
        */

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        NetworkManager.builder(this)
            .setDebugLog(true)
            .addBaseUrl("https://www.oriana.com")
            .build()

        networkCall()
    }

    private fun networkCall() {
        val networkBuilder: NetworkBuilder = NetworkBuilder()
            .setContext(this)
            .setRequestType(RequestType.GET)
            .setHeaderParams(hashMapOf("Authorization" to "Bearer yl3h5s5gfxucs74f1j9pq5s28apojabk"))
            .setUrl("https://www.oriana.com/index.php/rest/V1/products?searchCriteria%5BpageSize%5D=24&searchCriteria%5BcurrentPage%5D=1&searchCriteria%5BfilterGroups%5D%5B0%5D%5Bfilters%5D%5B0%5D%5Bfield%5D=status&searchCriteria%5BfilterGroups%5D%5B0%5D%5Bfilters%5D%5B0%5D%5Bvalue%5D=1&searchCriteria%5BfilterGroups%5D%5B0%5D%5Bfilters%5D%5B0%5D%5Bcondition_type%5D=eq&searchCriteria%5BfilterGroups%5D%5B1%5D%5Bfilters%5D%5B0%5D%5Bfield%5D=category_id&searchCriteria%5BfilterGroups%5D%5B1%5D%5Bfilters%5D%5B0%5D%5Bvalue%5D=61&searchCriteria%5BfilterGroups%5D%5B1%5D%5Bfilters%5D%5B0%5D%5BconditionType%5D=eq&searchCriteria%5BsortOrders%5D%5B0%5D%5Bdirection%5Dvalue=DESC&searchCriteria%5BsortOrders%5D%5B0%5D%5Bfield%5D=new_arrival_date")
            .setListener(object : NetworkListener {
                override fun onSuccess(response: JsonElement?, requestCode: Int) {
                    //val user = LoganSquare.parse(response?.asJsonObject.toString(), User::class.java)
                    Log.d("response", "Got onSuccess: "+response.toString())
                    var gson = Gson()
                    var responseDataModel: ResponseModel = gson.fromJson(response.toString(), ResponseModel::class.java)
                    Log.d("response", "Got responseDataModel: "+responseDataModel)
                    var itemList: List<ItemsItem> = responseDataModel.items as List<ItemsItem>
                    Log.d("Response", "## count: "+itemList.size+", total_count: "+responseDataModel.totalCount)

                    adapter = CustomAdapter(itemList as List<ItemsItem>, this@MainActivity)
                    val gridLayoutManager = GridLayoutManager(this@MainActivity,2)
                    recyclerView!!.adapter = adapter
                    recyclerView!!.setHasFixedSize(true)
                    recyclerView!!.layoutManager = gridLayoutManager
                }

                override fun onError(error: NetworkError) {
                    Log.d("Failed", "onError!!")
                }
            })
        networkBuilder.request()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}