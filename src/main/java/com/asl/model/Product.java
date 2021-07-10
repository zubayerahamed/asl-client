/**
 * 
 */
package com.asl.model;

import java.math.BigDecimal;

import lombok.Data;

/**
 * @author zubayer
 *
 */
@Data
public class Product {

	private Long pid;
	private String name;
	private BigDecimal rate;
	private String unit;
}
