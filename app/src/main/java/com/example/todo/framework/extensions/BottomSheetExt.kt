package com.example.todo.framework.extensions

import com.google.android.material.bottomsheet.BottomSheetDialogFragment

fun BottomSheetDialogFragment.closeBottomSheet() = this.dialog?.dismiss()