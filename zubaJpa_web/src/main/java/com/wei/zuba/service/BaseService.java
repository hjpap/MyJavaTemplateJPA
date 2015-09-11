package com.wei.zuba.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wei.zuba.cfg.RestClientConfig;
import com.wei.zuba.utils.RestClient;

@Service
public class BaseService {
	@Autowired
	RestClient restClient;

	@Autowired
	RestClientConfig clientConfig;
}
