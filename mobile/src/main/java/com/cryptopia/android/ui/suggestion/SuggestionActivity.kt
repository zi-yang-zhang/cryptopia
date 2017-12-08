package com.cryptopia.android.ui.suggestion

import android.os.Bundle
import com.cryptopia.android.R
import dagger.android.support.DaggerAppCompatActivity

/**
 * Created by robertzzy on 07/12/17.
 */
class SuggestionActivity : DaggerAppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_suggestion)

    }
}