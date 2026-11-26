
package com.sanusi.mixer

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.*

class MainActivity : Activity() {

    private val green = Color.rgb(0, 255, 110)
    private val dark = Color.rgb(15, 17, 18)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = dark
        window.navigationBarColor = dark
        window.decorView.systemUiVisibility = 0

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(20, 20, 20, 20)
            setBackgroundColor(dark)
        }

        val scroll = ScrollView(this)
        val content = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
        }

        val title = TextView(this).apply {
            text = "MIXER by Sanusi"
            textSize = 27f
            setTextColor(green)
            gravity = Gravity.CENTER
            setPadding(0, 15, 0, 8)
        }
        content.addView(title)

        addLabel(content, "PROFESSIONAL AUDIO EQUALIZER")

        addButton(content, "POWER  ON")
        addLabel(content, "10 BAND EQUALIZER")

        val bands = listOf(
            "31 Hz", "62 Hz", "125 Hz", "250 Hz",
            "500 Hz", "1 kHz", "2 kHz", "4 kHz",
            "8 kHz", "16 kHz"
        )

        for (band in bands) {
            val row = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
            }

            val label = TextView(this).apply {
                text = "$band     0 dB"
                textSize = 14f
                setTextColor(Color.WHITE)
            }

            val seek = SeekBar(this).apply {
                max = 24
                progress = 12
                progressTintList =
                    android.content.res.ColorStateList.valueOf(green)
                thumbTintList =
                    android.content.res.ColorStateList.valueOf(green)
            }

            seek.setOnSeekBarChangeListener(
                object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(
                        bar: SeekBar?,
                        value: Int,
                        user: Boolean
                    ) {
                        label.text =
                            "$band     ${(value - 12) / 2} dB"
                    }
                    override fun onStartTrackingTouch(bar: SeekBar?) {}
                    override fun onStopTrackingTouch(bar: SeekBar?) {}
                }
            )

            row.addView(label)
            row.addView(seek)
            content.addView(row)
        }

        addLabel(content, "SOUND EFFECTS")

        addButton(content, "BASS BOOST")
        addButton(content, "TREBLE")
        addButton(content, "VIRTUALIZER")
        addButton(content, "REVERB")

        addLabel(content, "SOUND PRESETS")

        addButton(content, "NORMAL")
        addButton(content, "POP")
        addButton(content, "ROCK")
        addButton(content, "JAZZ")
        addButton(content, "CLASSICAL")
        addButton(content, "VOCAL")
        addButton(content, "DANGDUT")

        addButton(content, "RESET EQUALIZER")

        scroll.addView(content)
        root.addView(
            scroll,
            LinearLayout.LayoutParams(
                -1, 0, 1f
            )
        )

        setContentView(root)
    }

    private fun addLabel(
        parent: LinearLayout,
        text: String
    ) {
        val label = TextView(this).apply {
            this.text = text
            textSize = 16f
            setTextColor(green)
            setPadding(0, 22, 0, 10)
        }
        parent.addView(label)
    }

    private fun addButton(
        parent: LinearLayout,
        text: String
    ) {
        val button = Button(this).apply {
            this.text = text
            textSize = 14f
            setTextColor(green)
            setBackgroundColor(Color.rgb(35, 42, 38))
            isAllCaps = false
        }
        val params = LinearLayout.LayoutParams(
            -1, 55
        )
        params.setMargins(0, 6, 0, 6)
        parent.addView(button, params)
    }
}
