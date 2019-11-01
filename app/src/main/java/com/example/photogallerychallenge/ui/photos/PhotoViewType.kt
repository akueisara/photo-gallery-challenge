package com.example.photogallerychallenge.ui.photos

import com.example.photogallerychallenge.R

enum class PhotoViewType(val value: Int) {
    LIST(1),
    GRID(3);

    companion object{
        fun switchViewTypeValue(value: Int): Int {
            return if(value == LIST.value) {
                GRID.value
            } else {
                LIST.value
            }
        }

        fun setMenuImageResId(value: Int): Int {
            return if(value == LIST.value) R.drawable.ic_list else R.drawable.ic_grid
        }
    }
}