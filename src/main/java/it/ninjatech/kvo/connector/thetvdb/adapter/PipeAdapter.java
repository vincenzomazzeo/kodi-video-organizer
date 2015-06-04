package it.ninjatech.kvo.connector.thetvdb.adapter;

import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.apache.commons.lang3.StringUtils;

public class PipeAdapter extends XmlAdapter<String, List<String>> {

	@Override
	public List<String> unmarshal(String value) throws Exception {
		return Arrays.asList(StringUtils.split(value, "|"));
	}

	@Override
	public String marshal(List<String> value) throws Exception {
		return StringUtils.join(value, "|");
	}

}
