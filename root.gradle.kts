plugins {
    alias(libs.plugins.pgt.root)
    alias(libs.plugins.kotlin) apply false
}

preprocess {
    val fabric12004 = createNode("1.20.4-fabric", 12004, "yarn")
}
