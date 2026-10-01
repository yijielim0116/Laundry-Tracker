package org.setu.laundrytracker.models

data class MachineModel(
    var id: Long = 0,
    var name: String = "",
    var location: String = "",
    var inUse: Boolean = false
)
