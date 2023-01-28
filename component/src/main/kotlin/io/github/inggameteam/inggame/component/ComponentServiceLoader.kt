package io.github.inggameteam.inggame.component

import io.github.inggameteam.inggame.component.componentservice.ComponentService
import io.github.inggameteam.inggame.component.componentservice.LayeredComponentServiceImp
import io.github.inggameteam.inggame.component.componentservice.MultiParentsComponentService
import io.github.inggameteam.inggame.component.componentservice.ResourceComponentServiceImp
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

fun loadComponentServiceModel() = ComponentServiceDSL("root", ArrayList(), ArrayList()).apply {
    "player" cs "player-instance" cs "multi-player" csc {
        "game-player" cs "game-instance" cs "language"
        "view-player" cs "language"
        "party-player" cs "party-instance" cs "language"
    }
    "language" key "language" csc {
        "resource" csc {
            "view-resource" cs "default"
            "custom-game" cs "game-resource" cs "default"
            "party-resource" cs "default"
        }
        "korean" cs "resource"
        "alien" cs "resource"
    }
}.registry.let { registry ->
    registry.map { cs ->
        module {
            single {
                if (cs.parents.size == 1)
                    MultiParentsComponentService(
                        cs.name,
                        { get(named(registry.first().name)) },
                        cs.parents.map { get(named(it)) },
                        cs.key
                    )
                else if (cs.isLayer) LayeredComponentServiceImp(
                    get(named(cs.name)),
                    get(),
                    get(named(cs.parents.first())),
                    cs.name
                )
                else ResourceComponentServiceImp(
                    get(named(cs.name)),
                    get(),
                    get(named(cs.parents.first())),
                    cs.name
                )
            } bind ComponentService::class
        }
    }
}