package com.android.todoapp

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.android.todoapp.databinding.ListItemBinding

class MyAdapter(private val myDataset: MutableList<String>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    private lateinit var alertDialog: AlertDialog.Builder
    private lateinit var dataBinding: ListItemBinding

    class MyViewHolder(dataBinding: ListItemBinding) : RecyclerView.ViewHolder(dataBinding.root) {
        dataBinding.apply {
            val todoText: TextView = itemView.findViewById(R.id.listItem)
            val deleteButton: Button = itemView.findViewById(R.id.buttonDelete)
            val editButton: Button = itemView.findViewById(R.id.buttonEdit)
        }
    }

    override fun onCreateViewHolder (parent: ViewGroup, viewType: Int): MyViewHolder {
        dataBinding = ListItemBinding.inflate(LayoutInflater.from(parent.context))
        return MyViewHolder(dataBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.apply {
            todoText.text = myDataset[position]
            deleteButton.setOnClickListener {
                myDataset.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, myDataset.size)
            }
            editButton.setOnClickListener {
                alertDialog = AlertDialog.Builder(holder.itemView.context)
                alertDialog.setTitle("Edit title")
                    .setView(
                        LayoutInflater.from(holder.itemView.context)
                            .inflate(R.layout.edit_item, null)
                    )
                    .setPositiveButton("Update", DialogInterface.OnClickListener{
                        dialogInterface, i ->
                            myDataset[position] = LayoutInflater.from(holder.itemView.context)
                                .inflate(R.layout.edit_item, null)
                                .findViewById<TextView>(R.id.editText).text.toString()
                    })
                    .setNegativeButton("Cancel", DialogInterface.OnClickListener{
                            dialogInterface, i ->
                    })
                alertDialog.create().show()
            }
        }
    }

    override fun getItemCount() = myDataset.size
}
