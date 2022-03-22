package com.geekbrains.myfirsttests.view.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.myfirsttests.R
import com.geekbrains.myfirsttests.model.SearchResult
import com.geekbrains.myfirsttests.view.search.SearchResultAdapter.SearchResultViewHolder

internal class SearchResultAdapter : RecyclerView.Adapter<SearchResultViewHolder>() {

  private var results: List<SearchResult> = listOf()

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): SearchResultViewHolder {
    return SearchResultViewHolder(
      LayoutInflater.from(parent.context).inflate(R.layout.list_item, null)
    )
  }

  override fun onBindViewHolder(
    holder: SearchResultViewHolder,
    position: Int
  ) {
    holder.bind(results[position])
  }

  override fun getItemCount(): Int {
    return results.size
  }

  fun updateResults(results: List<SearchResult>) {
    this.results = results
    notifyDataSetChanged()
  }

  internal class SearchResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(searchResult: SearchResult) {
      itemView.findViewById<TextView>(R.id.repositoryName).text = searchResult.fullName
      itemView.findViewById<TextView>(R.id.repositoryName).setOnClickListener {
        Toast.makeText(itemView.context, searchResult.fullName, Toast.LENGTH_SHORT).show()
      }
    }
  }
}