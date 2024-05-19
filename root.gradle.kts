plugins {
    alias(libs.plugins.pgt.root)
    alias(libs.plugins.kotlin) apply false
}

preprocess {
    val fabric12006 = createNode("1.20.6-fabric", 12006, "yarn")
}
