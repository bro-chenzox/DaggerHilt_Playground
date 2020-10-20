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
import javax.inject.Singleton

@AndroidEntryPoint // Class to which we want inject another class(es) annotated
class MainActivity : AppCompatActivity() {

    @Inject lateinit var someClass: SomeClass // Example of field DI (Dependency injection)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println(someClass.toDoSomeThing())
    }
}

class SomeClass @Inject constructor(
        private val someInterfaceImpl: SomeInterface, // Example of constructor DI
        private val gson: Gson                        // Example of constructor DI
) {
    fun toDoSomeThing() = someInterfaceImpl.toDoSomeThingMore()
}

class SomeInterfaceImpl @Inject constructor() : SomeInterface {
    override fun toDoSomeThingMore() = "I did some more"
}

interface SomeInterface {
    fun toDoSomeThingMore(): String
}

/**Module for injecting of interfaces*/
@Module
@InstallIn(ApplicationComponent::class)
abstract class InterfaceModule {

    @Singleton
    @Binds
    abstract fun bindInterface(someInterfaceImpl: SomeInterfaceImpl): SomeInterface
}

/**Module for injecting of third-party classes*/
@Module
@InstallIn(ActivityComponent::class) //Component must coincide with Scope Annotation below
class GsonModule {

    @ActivityScoped //Scope Annotation
    @Provides
    fun provideGson(): Gson = Gson()
}