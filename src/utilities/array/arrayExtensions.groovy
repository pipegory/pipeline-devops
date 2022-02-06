package utilities.array
​
def searchKeyInArray(String keyWordsAsString, String splitIdentifier, Map arrayMapToCompare){
    def _array = []
    keyWordsAsString.split("${splitIdentifier}").each{
        def _key = it?.trim()
        if(!_key.equals("") && ( arrayMapToCompare.containsKey(it) )){
            _array.add(arrayMapToCompare[it])
        }else{
            //it could be 'error'
            println("===============================================================")
            figlet  " ${it} "
            println "No existen coincidencias, sugerencias:${arrayMapToCompare.keySet() as List}"
            println("===============================================================")
        }
    }
    return _array
}
return this;
