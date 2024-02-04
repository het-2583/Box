import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.box.ImageInfo
import com.example.box.R
import com.squareup.picasso.Picasso

class ShowGroundAdapter : RecyclerView.Adapter<ShowGroundAdapter.ViewHolder>() {
    private var groundList: List<ImageInfo> = emptyList()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tvName)
        val address: TextView = itemView.findViewById(R.id.tvAddress)
        val imageView: ImageView = itemView.findViewById(R.id.ivImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ground, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return groundList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = groundList[position]

        // Update TextViews
        holder.name.text = currentItem.boxName
        holder.address.text = currentItem.address

        // Load image using Picasso
        Picasso.get().load(currentItem.imageUrl).into(holder.imageView)
    }

    fun updateGroundList(newGroundList: List<ImageInfo>) {
        groundList = newGroundList
        notifyDataSetChanged()
    }
}
