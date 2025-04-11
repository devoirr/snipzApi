package me.snipz.api.service

import kotlin.reflect.KClass

interface Service {
    fun onEnable()
    fun onDisable()
}

class ServiceManager {

    private val services = mutableMapOf<KClass<*>, Any>()

    fun <T: Service> register(clazz: KClass<T>, instance: T) {
        services[clazz] = instance
    }

    fun <T: Service> getService(clazz: KClass<T>): Any? {
        return services[clazz]
    }

    fun enableService(clazz: KClass<*>) {
        (services[clazz] as Service).onEnable()
    }

    fun disableService(clazz: KClass<*>) {
        (services[clazz] as Service).onDisable()
    }

}