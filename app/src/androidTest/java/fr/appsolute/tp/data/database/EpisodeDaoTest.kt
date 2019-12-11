package fr.appsolute.tp.data.database

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

class EpisodeDaoTest {

    @Before
    fun setUp(){

    }

    @After
    fun tearDown(){

    }

    @Test
    fun selectAll(){

    }

    @Test
    fun insertAll(){

    }

    companion object{

        private var database: RickAndMortyDatabase? = null

        @JvmStatic
        @BeforeClass
        fun createDatabase(): RickAndMortyDatabase{
            return database ?: Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getInstrumentation()
                    .targetContext,
                RickAndMortyDatabase::class.java
            ).build().also {
                database = it
            }
        }
    }
}