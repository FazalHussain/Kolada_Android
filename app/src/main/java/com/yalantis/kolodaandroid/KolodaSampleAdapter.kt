package com.yalantis.kolodaandroid

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_koloda.view.*


/**
 * Created by anna on 11/10/17.
 */
class KolodaSampleAdapter(val context: Context,val data: List<Data>?, val updateListener: UpdateListener) : BaseAdapter() {

    private val dataList = mutableListOf<Data>()

    init {
        if (data != null) {
            dataList.addAll(data)
        }
    }

    override fun getCount(): Int {
        return dataList.size
    }

    override fun getItem(position: Int): Data {
        return dataList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setData(data: List<Data>) {
        dataList.clear()
        dataList.addAll(data)
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val holder: DataViewHolder
        var view = convertView

        if (view == null) {
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_koloda, parent, false)
            holder = DataViewHolder(view)
            view?.tag = holder
        } else {
            holder = view.tag as DataViewHolder
        }

        holder.bindData(context, getItem(position))

        return view
    }

    /**
     * Static view items holder
     */
    inner class DataViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var picture = view.kolodaImage


        internal fun bindData(context: Context, data: Data) {
            val transforms = RequestOptions().transforms(CenterCrop(), RoundedCorners(20))
            Glide.with(context)
                    .load(data.drawableId)
                    .apply(transforms)
                    .into(picture)
            updateListener.updateData(data)
        }

    }
}

interface UpdateListener {
    fun updateData(data: Data)
}

data class Data(val drawableId: Int, val textTitle: String)