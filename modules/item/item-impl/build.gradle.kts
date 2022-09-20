allprojects {
    dependencies {
        api(project(":modules:item:item-api"))
        api(project(":modules:mongodb"))
    }
}