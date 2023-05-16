package com.code.challenge.trading.service;

import com.code.challenge.trading.configuration.SignalConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
@Slf4j
@RequiredArgsConstructor
public class SignalHandlerImpl implements SignalHandler {

    @Autowired
    SignalConfiguration signalConfiguration;

    @Autowired
    Algo algo;

    @Override
    public void handleSignal(int signal) {

        // Converting the integer input to string data
        String string_number = Integer.toString(signal);
        for(int i = 0; i < string_number.length(); i++){
            String expression = signalConfiguration.mapSignalSpecification().get(String.valueOf(string_number.charAt(i)));
            if(null != expression) {
                log.info(expression);
                switch (expression) {
                    case "setup":
                        algo.setUp();
                        log.info("Signal setup execution successful");
                        break;
                    case "reverse":
                        algo.reverse();
                        log.info("Signal reverse execution successful");
                        break;
                    case "setalgoparam":
                        algo.setAlgoParam(1, 20);
                        log.info("Signal setalgoparam execution successful");
                        break;
                    case "performcalc":
                        algo.performCalc();
                        log.info("Signal performCalc execution successful");
                        break;
                    case "submittomarket":
                        algo.submitToMarket();
                        log.info("Signal submitToMarket execution successful");
                        break;
                    default:
                        algo.cancelTrades();
                        log.info("Signal cancelTrades execution successful");
                        break;
                }
            } else {
                throw new HttpClientErrorException(HttpStatus.UNPROCESSABLE_ENTITY);
            }
        }
        algo.doAlgo();
        log.info("Signal execution successfully");
    }
}
