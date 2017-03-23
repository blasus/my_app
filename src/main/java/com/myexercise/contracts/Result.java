package com.myexercise.contracts;

/**
 * @author Blasi Francesco
 * @version 0.0.1-SNAPSHOT
 */

import java.util.List;

/**
 * Class support used to prepare json object will be sent to client as GET response.
 * It's characterized from the most important instances will be necessary to the correct
 * utilization of DataTables:
 * <ul>
 * <li>a draw key to identify the aknowledge in the comunication;
 * <li>recordsTotal, an integer representing the number of contracts stored in DB;
 * <li>data, the entries list returned from the call rest;
 * <li>error, a string used to tell a status error to the client.
 * </ul>
 * 
 *
 */
public class Result {

	int draw;
	long recordsTotal;
	int recordsFiltered;
	List<Contract> data;
	String error;
	
	/**
	 * Class constructor without parameters.
	 */
	public Result(){}
	
	/**
	 * Class constructor used when there won't be any error reported in the response.
	 * 
	 * @param draw  draw
	 * @param rTotal  recordsTotal
	 * @param rFiltered  recordsFiltered
	 * @param data  data
	 */
	public Result(int draw, long rTotal, int rFiltered, List<Contract> data) {
		this.draw = draw;
		this.recordsTotal = rTotal;
		this.recordsFiltered = rFiltered;
		this.data = data;
	}

	//getter and setter methods:
	
	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public long getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(int recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public int getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(int recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public List<Contract> getData() {
		return data;
	}

	public void setData(List<Contract> data) {
		this.data = data;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
}
