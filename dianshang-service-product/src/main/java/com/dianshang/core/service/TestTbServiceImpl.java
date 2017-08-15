package com.dianshang.core.service;

import com.dianshang.core.dao.TestTbDAO;
import com.dianshang.core.pojo.TestTb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 测试类服务实现类
 *
 * @author Administrator
 *
 */
@Service("testTbService")
@Transactional
public class TestTbServiceImpl implements TestTbService {

	@Autowired
	private TestTbDAO testTbDAO;

	@Override
	public void add(TestTb testTb) {
		testTbDAO.add(testTb);
	}
}
