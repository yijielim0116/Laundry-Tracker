package org.setu.laundrytracker.main

import org.setu.laundrytracker.models.MachineMemStore
import org.setu.laundrytracker.models.MachineStore

object AppData {
    val machines: MachineStore = MachineMemStore()
}
