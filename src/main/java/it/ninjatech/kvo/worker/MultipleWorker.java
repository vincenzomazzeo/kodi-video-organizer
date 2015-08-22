package it.ninjatech.kvo.worker;

import java.util.LinkedHashMap;
import java.util.Map;

public class MultipleWorker extends AbstractWorker<MultipleWorker.Result> {

	private final Map<String, AbstractWorker<?>> workers;
	
	public MultipleWorker() {
		this.workers = new LinkedHashMap<>();
	}
	
	@Override
	public Result work() throws Exception {
		Result result = new Result();
		
		for (String id : this.workers.keySet()) {
			AbstractWorker<?> worker = this.workers.get(id);
			result.addResult(id, worker.work());
		}
		
		return result;
	}
	
	public void addWorker(String id, AbstractWorker<?> worker) {
		this.workers.put(id, worker);
	}
	
	public static class Result {
		
		private final Map<String, Object> result;
		
		private Result() {
			this.result = new LinkedHashMap<>();
		}
		
		private void addResult(String id, Object result) {
			this.result.put(id, result);
		}
		
		public Map<String, Object> getResult() {
			return this.result;
		}
		
	}

}
