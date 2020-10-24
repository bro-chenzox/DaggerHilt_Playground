package com.palchak.sergey.daggerhiltplayground

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject
import javax.inject.Qualifier
import javax.inject.Singleton

@AndroidEntryPoint // Class to which we want inject another class(es) annotated
class MainActivity : AppCompatActivity() {

    @Inject lateinit var someClass: SomeClass // Example of field DI (Dependency injection)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println(someClass.toDoSomeThing1())
        println(someClass.toDoSomeThing2())
    }
}

class SomeClass @Inject constructor(
        @Impl1 private val someInterfaceImpl1: SomeInterface, // Example of constructor DI
        @Impl2 private val someInterfaceImpl2: SomeInterface, // Example of constructor DI
        private val gson: Gson                        // Example of constructor DI
) {
    fun toDoSomeThing1() = someInterfaceImpl1.toDoSomeThingMore()
    fun toDoSomeThing2() = someInterfaceImpl2.toDoSomeThingMore()
}

class SomeInterfaceImpl1 @Inject constructor() : SomeInterface {
    override fun toDoSomeThingMore() = "I did some more 1"
}

class SomeInterfaceImpl2 @Inject constructor() : SomeInterface {
    override fun toDoSomeThingMore() = "I did some more 2"
}

interface SomeInterface {
    fun toDoSomeThingMore(): String
}

/**Module for injecting of interfaces*/
@Module
@InstallIn(ApplicationComponent::class)
abstract class InterfaceModule {

    @Impl1
    @Singleton
    @Binds
    abstract fun bindInterface1(someInterfaceImpl1: SomeInterfaceImpl1): SomeInterface

    @Impl2
    @Singleton
    @Binds
    abstract fun bindInterface2(someInterfaceImpl2: SomeInterfaceImpl2): SomeInterface
}

/**Module for injecting of third-party classes*/
@Module
@InstallIn(ActivityComponent::class) //Component must coincide with Scope Annotation below
class GsonModule {

    @ActivityScoped //Scope Annotation
    @Provides
    fun provideGson(): Gson = Gson()
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Impl1

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Impl2