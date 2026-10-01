package org.setu.laundrytracker

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.setu.laundrytracker.models.MachineModel

class AddEditActivity : AppCompatActivity() {

    private lateinit var nameInput: EditText
    private lateinit var locationInput: EditText
    private lateinit var inUseCheckBox: CheckBox

    private var editingId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createUserInterface()

        editingId = intent.getLongExtra("id", -1L)

        if (editingId != -1L) {
            loadExistingMachine(editingId!!)
        }
    }

    private fun createUserInterface() {

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 32, 32, 32)
        }

        nameInput = EditText(this).apply {
            hint = "Name (e.g. Washer 1)"
        }

        locationInput = EditText(this).apply {
            hint = "Location (e.g. Block A, Floor 1)"
        }

        inUseCheckBox = CheckBox(this).apply {
            text = "Currently in use"
        }

        val saveButton = Button(this).apply {
            text = "Save"

            setOnClickListener {
                saveMachine()
            }
        }

        val cancelButton = Button(this).apply {
            text = "Cancel"

            setOnClickListener {
                finish()
            }
        }

        root.addView(nameInput)
        root.addView(locationInput)
        root.addView(inUseCheckBox)
        root.addView(saveButton)
        root.addView(cancelButton)

        setContentView(root)
    }

    private fun loadExistingMachine(id: Long) {

        val machine = AppData.machines.findOne(id)

        if (machine == null) {
            Toast.makeText(
                this,
                "Machine not found",
                Toast.LENGTH_SHORT
            ).show()

            finish()
            return
        }

        nameInput.setText(machine.name)
        locationInput.setText(machine.location)
        inUseCheckBox.isChecked = machine.inUse
    }

    private fun saveMachine() {

        val name = nameInput.text.toString().trim()
        val location = locationInput.text.toString().trim()

        if (name.isEmpty()) {
            nameInput.error = "Name is required"
            return
        }

        if (editingId == null || editingId == -1L) {

            val machine = MachineModel(
                name = name,
                location = location,
                inUse = inUseCheckBox.isChecked
            )

            AppData.machines.create(machine)

            Toast.makeText(
                this,
                "Machine created",
                Toast.LENGTH_SHORT
            ).show()

        } else {

            val machine = MachineModel(
                id = editingId!!,
                name = name,
                location = location,
                inUse = inUseCheckBox.isChecked
            )

            AppData.machines.update(machine)

            Toast.makeText(
                this,
                "Machine updated",
                Toast.LENGTH_SHORT
            ).show()
        }

        finish()
    }
}
