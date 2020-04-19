package com.quentin.sierocki.legume.back.ti;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.quentin.sierocki.legume.back.SpringbootRestApplication;
import com.quentin.sierocki.legume.back.domain.repository.CommandProductRepository;
import com.quentin.sierocki.legume.back.domain.repository.CommandRepository;
import com.quentin.sierocki.legume.back.domain.repository.ProductRepository;
import com.quentin.sierocki.legume.back.domain.repository.ProductTypeRepository;
import com.quentin.sierocki.legume.back.domain.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SpringbootRestApplication.class })
@TestPropertySource({ "classpath:application.test.properties" })
public class SpringBootRestApplicationTest {

	@Rule
	public TestName name = new TestName();

	@Before
	public void prepareContext() {
		System.out.println("########### " + "->" + this.name.getMethodName() + " ###########");
		deleteAllTable();
	}

	protected Logger logger = LoggerFactory.getLogger(SpringBootRestApplicationTest.class);

	@Autowired
	protected ProductTypeRepository productTypeRepository;

	@Autowired
	protected ProductRepository productRepository;

	@Autowired
	protected UserRepository userRepository;

	@Autowired
	protected CommandRepository commandRepository;

	@Autowired
	protected CommandProductRepository commandProductRepository;

	protected void deleteAllTable() {
		commandProductRepository.deleteAll();
		commandRepository.deleteAll();

		productRepository.deleteAll();
		productTypeRepository.deleteAll();
		userRepository.deleteAll();
	}

}
