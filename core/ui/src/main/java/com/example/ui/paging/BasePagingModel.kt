package com.example.ui.paging


interface BaseDataModel

interface BasePagingModel<T : BaseDataModel> {
    val pagingDataItems: List<T>
}
