package com.aitec.sitesport.lib.di

import com.aitec.sitesport.lib.EventBusImp
import com.aitec.sitesport.lib.base.EventBusInterface
import dagger.Module
import dagger.Provides
import org.greenrobot.eventbus.EventBus
import javax.inject.Singleton

@Module
class LibModule {

    @Provides
    @Singleton
    fun providesEventBus(eventBus: EventBus): EventBusInterface {
        return EventBusImp(eventBus);
    }

    @Provides
    @Singleton
    fun providesLibraryEventBus(): EventBus {
        return EventBus.getDefault()
    }
}