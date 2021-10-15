package com.mrright.map.presentation

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mrright.map.R
import com.mrright.map.databinding.ItemOfficeBinding
import com.mrright.map.models.OfficeDetail
import com.mrright.map.utils.setIfNullOrEmpty

class OfficeAdapter(
    private val onClick: (Intent) -> Unit,
) : ListAdapter<OfficeDetail, OfficeAdapter.OfficeViewHolder>(DetailsUtil()) {

    inner class OfficeViewHolder(val bind: ItemOfficeBinding) : RecyclerView.ViewHolder(bind.root) {

        init {

            bind.cardOfficeName.setOnClickListener {
                currentList[adapterPosition].isExpanded = !currentList[adapterPosition].isExpanded
                notifyItemChanged(adapterPosition)
            }

            with(bind.viewDetails) {

                consDirection.setOnClickListener {
                    val office = currentList[adapterPosition]
                    if (!office.latitute.isNullOrBlank() && !office.longitute.isNullOrBlank()) {
                        onClick(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("geo:${office.latitute},${office.longitute},z=20"),
                            )
                        )
                    }
                }
                consCall.setOnClickListener {
                    val office = currentList[adapterPosition]
                    if (!office.officePhone.isNullOrBlank()) {
                        val phone = office.officePhone.replace(" ", "").removePrefix("P:")
                        onClick(Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone")))
                    }
                }
                consWebsite.setOnClickListener {
                    val office = currentList[adapterPosition]
                    if (!office.officeWebsite.isNullOrBlank()) {
                        onClick(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://${office.officeWebsite}")
                            )
                        )
                    }
                }
                consEmail.setOnClickListener {
                    val office = currentList[adapterPosition]
                    if (!office.officeEmail.isNullOrBlank()) {
                        onClick(Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_EMAIL, arrayOf(office.officeEmail))
                        })
                    }
                }
                consFacebook.setOnClickListener {
                    val office = currentList[adapterPosition]
                    if (!office.officeFacebook.isNullOrBlank()) {
                        onClick(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://www.facebook.com/${office.officeFacebook}")
                            )
                        )
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfficeViewHolder {
        return OfficeViewHolder(
            ItemOfficeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OfficeViewHolder, position: Int) {

        val office = currentList[position]

        with(holder.bind) {
            txtOfficeName.setIfNullOrEmpty(office.officeName)
            viewDetails.txtDirections.also {
                it.text = it.context.getString(
                    R.string.address,
                    office.officeAddress1,
                    office.officeAddress2,
                )
            }
            viewDetails.txtCall.setIfNullOrEmpty(office.officePhone)
            viewDetails.txtEmail.setIfNullOrEmpty(office.officeEmail)
            viewDetails.txtFacebook.setIfNullOrEmpty(office.officeFacebook)
            viewDetails.txtWebsite.setIfNullOrEmpty(office.officeWebsite)

            imgExpand.setImageResource(
                if (office.isExpanded)
                    R.drawable.ic_baseline_keyboard_arrow_up_24
                else R.drawable.ic_baseline_keyboard_arrow_down_24
            )
            viewDetails.root.visibility = if (office.isExpanded) View.VISIBLE else View.GONE
        }

    }

    override fun getItemCount() = currentList.size

}


class DetailsUtil : DiffUtil.ItemCallback<OfficeDetail>() {

    override fun areItemsTheSame(oldItem: OfficeDetail, newItem: OfficeDetail): Boolean {
        return oldItem.officePhone == newItem.officePhone
    }

    override fun areContentsTheSame(oldItem: OfficeDetail, newItem: OfficeDetail): Boolean {
        return oldItem == newItem
    }

}