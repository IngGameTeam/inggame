package io.github.inggameteam.inggame.component.view.model

import io.github.inggameteam.inggame.component.componentservice.ComponentService

class ComponentServiceViewImp(view: View, override val componentService: ComponentService)
    : ComponentServiceView, View by view