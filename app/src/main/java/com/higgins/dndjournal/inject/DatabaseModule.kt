package com.higgins.dndjournal.inject

import android.content.Context
import androidx.room.Room
import com.higgins.dndjournal.db.DndJournalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): DndJournalDatabase {
        return Room.databaseBuilder(context, DndJournalDatabase::class.java, "default-database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides fun provideCampaignDao(db: DndJournalDatabase) = db.campaignDao()
    @Provides fun provideQuestDao(db: DndJournalDatabase) = db.questDao()
    @Provides fun provideLocationDao(db: DndJournalDatabase) = db.locationDao()
}