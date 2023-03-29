package androidx.lifecycle //DO NOT UPDATE THIS PACKAGE
// We need to access LiveData's package visible method getVersion() for comparison

fun LiveData<*>.dataVersion(): Int {
    return this.version
}

