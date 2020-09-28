package io.github.buss;

import com.jayway.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CompletableFuture;

import static com.jayway.restassured.RestAssured.given;
import static java.lang.Thread.sleep;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BankControllerIT {

    @LocalServerPort
    private int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }


	//Teste para validar uma retirada e logo na sequência um depósito (sendo que a retirada ainda está sendo processada)
	@Test
    public void testConcurrentCall() throws InterruptedException {
		CompletableFuture.runAsync(this::cashOut);
		sleep(1000);
		deposit();

    }

	private void cashOut() {
		given()
				.when()
				.post("/bank/cashOut/" + 500.00).prettyPrint();
	}

	private void deposit() {
		//Como a operação acima ainda está sendo executada (por conta dos 3 segundos que colocamos no serviço para simular um processamento lento)
		//Ao executar a operação de depósito, o bulkhead não permitirá o acesso ao método de depósito
		//pois está definido em nossa configuração que só é permitido um acesso por vez ao Bulkhead "bank" (maxConcurrentCalls)
		given()
				.when()
				.post("/bank/deposit/" + 1000.00).prettyPrint();
//		given()
//				.when()
//				.post("/bank/deposit/" + 1000.00)
//				.then()
//				.statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
//				.body("message", equalTo("Bulkhead 'bank' is full and does not permit further calls"));
	}
}

