package com.wirebarley.exchange.data.repo

import com.google.gson.Gson
import com.wirebarley.exchange.data.di.IoDispatcher
import com.wirebarley.exchange.data.source.remote.api.CurrencyLayerAPI
import com.wirebarley.exchange.data.source.remote.response.LiveCurrencyData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton


interface CurrencyLayerRepository {
    suspend fun getLiveCurrencyData(): LiveCurrencyData
}

class CurrencyLayerRepositoryImpl @Inject constructor(
    private val currencyLayerAPI: CurrencyLayerAPI,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : CurrencyLayerRepository {

    override suspend fun getLiveCurrencyData(): LiveCurrencyData = withContext(dispatcher) {
        currencyLayerAPI.getLiveCurrencyData()
    }

}

class TestCurrencyLayerRepositoryImpl @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : CurrencyLayerRepository {

    override suspend fun getLiveCurrencyData(): LiveCurrencyData = withContext(dispatcher) {
        delay(1500)
        Gson().fromJson(testData, LiveCurrencyData::class.java)
    }

}

const val testData = "{\n" +
        "    \"success\": true,\n" +
        "    \"timestamp\": 1682530563,\n" +
        "    \"source\": \"USD\",\n" +
        "    \"quotes\": {\n" +
        "        \"USDAED\": 3.672304,\n" +
        "        \"USDAFN\": 86.380273,\n" +
        "        \"USDALL\": 100.550542,\n" +
        "        \"USDAMD\": 387.330208,\n" +
        "        \"USDANG\": 1.803382,\n" +
        "        \"USDAOA\": 507.047498,\n" +
        "        \"USDARS\": 221.582035,\n" +
        "        \"USDAUD\": 1.513775,\n" +
        "        \"USDAWG\": 1.8025,\n" +
        "        \"USDAZN\": 1.703157,\n" +
        "        \"USDBAM\": 1.771887,\n" +
        "        \"USDBBD\": 2.020311,\n" +
        "        \"USDBDT\": 106.229164,\n" +
        "        \"USDBGN\": 1.768604,\n" +
        "        \"USDBHD\": 0.376921,\n" +
        "        \"USDBIF\": 2083.810853,\n" +
        "        \"USDBMD\": 1,\n" +
        "        \"USDBND\": 1.336208,\n" +
        "        \"USDBOB\": 6.914202,\n" +
        "        \"USDBRL\": 5.041705,\n" +
        "        \"USDBSD\": 1.00063,\n" +
        "        \"USDBTC\": 3.3535148e-05,\n" +
        "        \"USDBTN\": 81.813323,\n" +
        "        \"USDBWP\": 13.209462,\n" +
        "        \"USDBYN\": 2.525638,\n" +
        "        \"USDBYR\": 19600,\n" +
        "        \"USDBZD\": 2.016932,\n" +
        "        \"USDCAD\": 1.362215,\n" +
        "        \"USDCDF\": 2138.000281,\n" +
        "        \"USDCHF\": 0.89197,\n" +
        "        \"USDCLF\": 0.029134,\n" +
        "        \"USDCLP\": 803.9053,\n" +
        "        \"USDCNY\": 6.926899,\n" +
        "        \"USDCOP\": 4532.6,\n" +
        "        \"USDCRC\": 531.520228,\n" +
        "        \"USDCUC\": 1,\n" +
        "        \"USDCUP\": 26.5,\n" +
        "        \"USDCVE\": 100.250281,\n" +
        "        \"USDCZK\": 21.302197,\n" +
        "        \"USDDJF\": 177.720277,\n" +
        "        \"USDDKK\": 6.753397,\n" +
        "        \"USDDOP\": 54.502977,\n" +
        "        \"USDDZD\": 135.612727,\n" +
        "        \"USDEGP\": 30.956302,\n" +
        "        \"USDERN\": 15,\n" +
        "        \"USDETB\": 54.139794,\n" +
        "        \"USDEUR\": 0.90611,\n" +
        "        \"USDFJD\": 2.22325,\n" +
        "        \"USDFKP\": 0.806621,\n" +
        "        \"USDGBP\": 0.80208,\n" +
        "        \"USDGEL\": 2.510011,\n" +
        "        \"USDGGP\": 0.806621,\n" +
        "        \"USDGHS\": 11.95042,\n" +
        "        \"USDGIP\": 0.806621,\n" +
        "        \"USDGMD\": 61.13006,\n" +
        "        \"USDGNF\": 8549.999834,\n" +
        "        \"USDGTQ\": 7.804891,\n" +
        "        \"USDGYD\": 211.628708,\n" +
        "        \"USDHKD\": 7.84995,\n" +
        "        \"USDHNL\": 24.594144,\n" +
        "        \"USDHRK\": 6.816133,\n" +
        "        \"USDHTG\": 155.593202,\n" +
        "        \"USDHUF\": 338.941014,\n" +
        "        \"USDIDR\": 14836.35,\n" +
        "        \"USDILS\": 3.627675,\n" +
        "        \"USDIMP\": 0.806621,\n" +
        "        \"USDINR\": 81.757011,\n" +
        "        \"USDIQD\": 1461,\n" +
        "        \"USDIRR\": 42249.999712,\n" +
        "        \"USDISK\": 135.839574,\n" +
        "        \"USDJEP\": 0.806621,\n" +
        "        \"USDJMD\": 153.579243,\n" +
        "        \"USDJOD\": 0.709397,\n" +
        "        \"USDJPY\": 133.822978,\n" +
        "        \"USDKES\": 135.849947,\n" +
        "        \"USDKGS\": 87.519957,\n" +
        "        \"USDKHR\": 4106.000051,\n" +
        "        \"USDKMF\": 445.497491,\n" +
        "        \"USDKPW\": 900.008089,\n" +
        "        \"USDKRW\": 1337.349843,\n" +
        "        \"USDKWD\": 0.30626,\n" +
        "        \"USDKYD\": 0.833824,\n" +
        "        \"USDKZT\": 455.19322,\n" +
        "        \"USDLAK\": 17275.000323,\n" +
        "        \"USDLBP\": 15150.210898,\n" +
        "        \"USDLKR\": 322.211552,\n" +
        "        \"USDLRD\": 163.825004,\n" +
        "        \"USDLSL\": 18.379603,\n" +
        "        \"USDLTL\": 2.95274,\n" +
        "        \"USDLVL\": 0.60489,\n" +
        "        \"USDLYD\": 4.766284,\n" +
        "        \"USDMAD\": 10.0625,\n" +
        "        \"USDMDL\": 18.011334,\n" +
        "        \"USDMGA\": 4419.999357,\n" +
        "        \"USDMKD\": 55.63229,\n" +
        "        \"USDMMK\": 2101.31995,\n" +
        "        \"USDMNT\": 3498.597723,\n" +
        "        \"USDMOP\": 8.090016,\n" +
        "        \"USDMRO\": 356.999828,\n" +
        "        \"USDMUR\": 45.14685,\n" +
        "        \"USDMVR\": 15.360081,\n" +
        "        \"USDMWK\": 1027.497441,\n" +
        "        \"USDMXN\": 18.08134,\n" +
        "        \"USDMYR\": 4.456977,\n" +
        "        \"USDMZN\": 63.250076,\n" +
        "        \"USDNAD\": 18.379771,\n" +
        "        \"USDNGN\": 464.500853,\n" +
        "        \"USDNIO\": 36.559563,\n" +
        "        \"USDNOK\": 10.63879,\n" +
        "        \"USDNPR\": 130.903276,\n" +
        "        \"USDNZD\": 1.63339,\n" +
        "        \"USDOMR\": 0.385008,\n" +
        "        \"USDPAB\": 1.00063,\n" +
        "        \"USDPEN\": 3.737503,\n" +
        "        \"USDPGK\": 3.524983,\n" +
        "        \"USDPHP\": 55.599497,\n" +
        "        \"USDPKR\": 283.999647,\n" +
        "        \"USDPLN\": 4.153455,\n" +
        "        \"USDPYG\": 7229.603323,\n" +
        "        \"USDQAR\": 3.641018,\n" +
        "        \"USDRON\": 4.4758,\n" +
        "        \"USDRSD\": 106.219741,\n" +
        "        \"USDRUB\": 82.124971,\n" +
        "        \"USDRWF\": 1113.5,\n" +
        "        \"USDSAR\": 3.751121,\n" +
        "        \"USDSBD\": 8.334311,\n" +
        "        \"USDSCR\": 13.890336,\n" +
        "        \"USDSDG\": 600.503454,\n" +
        "        \"USDSEK\": 10.33122,\n" +
        "        \"USDSGD\": 1.335496,\n" +
        "        \"USDSHP\": 1.21675,\n" +
        "        \"USDSLE\": 22.602694,\n" +
        "        \"USDSLL\": 19750.000419,\n" +
        "        \"USDSOS\": 568.504144,\n" +
        "        \"USDSRD\": 37.298196,\n" +
        "        \"USDSTD\": 20697.981008,\n" +
        "        \"USDSVC\": 8.755328,\n" +
        "        \"USDSYP\": 2512.049197,\n" +
        "        \"USDSZL\": 18.380032,\n" +
        "        \"USDTHB\": 34.130499,\n" +
        "        \"USDTJS\": 10.92674,\n" +
        "        \"USDTMT\": 3.5,\n" +
        "        \"USDTND\": 3.049983,\n" +
        "        \"USDTOP\": 2.36615,\n" +
        "        \"USDTRY\": 19.422904,\n" +
        "        \"USDTTD\": 6.795523,\n" +
        "        \"USDTWD\": 30.7185,\n" +
        "        \"USDTZS\": 2349.999716,\n" +
        "        \"USDUAH\": 36.957768,\n" +
        "        \"USDUGX\": 3762.320626,\n" +
        "        \"USDUYU\": 38.938743,\n" +
        "        \"USDUZS\": 11384.46346,\n" +
        "        \"USDVEF\": 2456463.492197,\n" +
        "        \"USDVES\": 2.596339,\n" +
        "        \"USDVND\": 23475,\n" +
        "        \"USDVUV\": 118.808507,\n" +
        "        \"USDWST\": 2.728979,\n" +
        "        \"USDXAF\": 594.313256,\n" +
        "        \"USDXAG\": 0.040424,\n" +
        "        \"USDXAU\": 0.000504,\n" +
        "        \"USDXCD\": 2.70255,\n" +
        "        \"USDXDR\": 0.741442,\n" +
        "        \"USDXOF\": 594.307871,\n" +
        "        \"USDXPF\": 108.425021,\n" +
        "        \"USDYER\": 250.350184,\n" +
        "        \"USDZAR\": 18.378097,\n" +
        "        \"USDZMK\": 9001.198835,\n" +
        "        \"USDZMW\": 17.675969,\n" +
        "        \"USDZWL\": 321.999592\n" +
        "    }\n" +
        "}"