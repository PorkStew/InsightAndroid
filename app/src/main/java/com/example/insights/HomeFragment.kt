import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.insights.ItemAdapter
import com.example.insights.R
import com.example.insights.adapters.forumadapter
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val bookvalues = ArrayList<HashMap<String,String>>()

        val root = inflater.inflate(R.layout.fragment_home, container, false)

        var recyclerView: RecyclerView = root.findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.layoutManager= LinearLayoutManager(context)
        val itemAdapter= ItemAdapter(this,bookvalues)
        recyclerView.adapter=(itemAdapter)
        val db = FirebaseFirestore.getInstance()
        db.collection("Books").addSnapshotListener { value, error ->
            if(error== null){
                if (value != null) {
                    for (change in value!!.documentChanges){
                        if(change.type == DocumentChange.Type.ADDED){
                            bookvalues.add(change.document.data as HashMap<String, String>)
                            val Itemadapter = ItemAdapter(this, bookvalues)
                            recyclerView.adapter = Itemadapter
                        }
                    }
                }
            }
        }

        return root
    }




}