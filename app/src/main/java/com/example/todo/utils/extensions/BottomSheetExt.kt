package com.example.todo.utils.extensions

import com.google.android.material.bottomsheet.BottomSheetDialogFragment

fun BottomSheetDialogFragment.closeBottomSheet() = this.dialog?.dismiss()