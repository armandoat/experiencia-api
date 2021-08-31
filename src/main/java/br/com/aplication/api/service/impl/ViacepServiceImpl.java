package br.com.aplication.api.service.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;

import br.com.aplication.api.domain.Endereco;
import br.com.aplication.api.dto.ConsultaCepDTO;
import br.com.aplication.api.service.ViacepService;

@Component
public class ViacepServiceImpl implements ViacepService {
	
	@Override
	public Endereco buscarCepPorNumeroCep(String cep) {
		//
		if(StringUtils.isEmpty(cep))
			return null;
		//
		String urlViacep = null;
		Endereco dto = null;
		//
		urlViacep = this.montarUrlViacepPorCep(cep);
		// Busca Endereço pelo Cep
		dto = (Endereco) buscarCep(urlViacep, true);
		return dto;
	}

	private String montarUrlViacepPorCep(String cep) {

		String url_ender = "http://viacep.com.br/ws/";
		//
		url_ender = url_ender.concat(cep);
		url_ender = url_ender.concat("/json/");
		//
		return url_ender;
	}

	private Endereco buscarCep(String urlViacep, boolean isBuscaPorCep) {

		try {
			//
			String json;
			URL url;
			url = new URL(urlViacep);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
			//
			try (InputStream is = urlConnection.getInputStream();

				BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
				StringBuilder jsonSb = new StringBuilder();
				//
				br.lines().forEach(l -> jsonSb.append(l.trim()));
				json = jsonSb.toString();
				//
				Gson gson = new Gson();
				Endereco dto = null;
				Endereco[] dtoArray = null;
				//
				if (isBuscaPorCep) {
					if(json.contains("erro"))
						return null;
					dto = gson.fromJson(json, Endereco.class);
					System.out.printf("Resultado Consulta CEP '%s' %n", dto);
					return Objects.nonNull(dto) ? dto : null;
				} else {
					// Na busca por endereço é esperado um array de endereços na resposta do serviço
					dtoArray = gson.fromJson(json, Endereco[].class);
					return Objects.nonNull(dtoArray) && Array.getLength(dtoArray) > 0 ? dtoArray[0] : null;
				}

			}

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return null;
	}
}
