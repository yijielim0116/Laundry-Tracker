package org.setu.laundrytracker.models

import java.util.concurrent.atomic.AtomicLong

class MachineMemStore : MachineStore {

    private val machines = ArrayList<MachineModel>()
    private val lastId = AtomicLong(0)

    override fun findAll(): List<MachineModel> = machines

    override fun create(machine: MachineModel) {
        machine.id = lastId.incrementAndGet()
        machines.add(machine)
    }

    override fun update(machine: MachineModel): Boolean {
        val foundIndex = machines.indexOfFirst { it.id == machine.id }
        return if (foundIndex != -1) {
            machines[foundIndex] = machine
            true
        } else {
            false
        }
    }

    override fun delete(id: Long): Boolean {
        val found = findOne(id)
        return if (found != null) {
            machines.remove(found)
            true
        } else {
            false
        }
    }

    override fun findOne(id: Long): MachineModel? = machines.find { it.id == id }
}
