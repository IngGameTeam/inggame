package io.github.bruce0203.updateman

import org.eclipse.jgit.api.Git
import java.io.File

fun pull(destiny: String) {
    val git = Git.open(File(destiny))
    git.stashCreate().call()
    git
        .pull().call().fetchResult.apply {
            println(this.messages)
        }
}