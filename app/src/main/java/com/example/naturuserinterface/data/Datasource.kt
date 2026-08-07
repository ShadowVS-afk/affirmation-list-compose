package com.example.naturuserinterface.data

import com.example.naturuserinterface.R
import com.example.naturuserinterface.model.Affirmation

class Datasource {
    fun loadAffirmation(): List<Affirmation> {
        return listOf(
            Affirmation(R.string.affirmation1, R.drawable.nature_icon),
            Affirmation(R.string.affirmation2, R.drawable.nature_icon),
            Affirmation(R.string.affirmation3, R.drawable.nature_icon),
            Affirmation(R.string.affirmation4, R.drawable.nature_icon),
            Affirmation(R.string.affirmation5, R.drawable.nature_icon),
            Affirmation(R.string.affirmation6, R.drawable.nature_icon),
            Affirmation(R.string.affirmation7, R.drawable.nature_icon),
            Affirmation(R.string.affirmation8, R.drawable.nature_icon),
            Affirmation(R.string.affirmation9, R.drawable.nature_icon),
            Affirmation(R.string.affirmation10, R.drawable.nature_icon),
            Affirmation(R.string.affirmation11, R.drawable.nature_icon),
            Affirmation(R.string.affirmation12, R.drawable.nature_icon),
            Affirmation(R.string.affirmation13, R.drawable.nature_icon),
            Affirmation(R.string.affirmation14, R.drawable.nature_icon),
            Affirmation(R.string.affirmation15, R.drawable.nature_icon),
        )
    }
}
