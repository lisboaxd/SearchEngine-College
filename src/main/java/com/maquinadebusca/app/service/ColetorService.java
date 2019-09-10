package com.maquinadebusca.app.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.maquinadebusca.app.model.Documento;
import com.maquinadebusca.app.repository.DocumentoRepository;

@Service
public class ColetorService implements DocumentoRepository {

	private List<URL> urls;

	public ColetorService() {
	}

	@Override
	public List<Documento> iniciar() {
		List<URL> totalUrl;
		List<Documento> totalDocumentos = null;

		Documento d;
		try {
			totalUrl = listarUrls();
			for (URL url : totalUrl) {
				d = new Documento();

				Document doc = Jsoup.connect(url.toString()).get();
				Elements links = doc.select("a[href]");
				d.setUrl(url);
				d.setTexto(doc.html());
				d.setVisao(doc.text());
				List<String> urls = new LinkedList();
				for (Element link : links)
					if ((!link.attr("abs:href").equals("") && (link.attr("abs:href") != null)))
						urls.add(link.attr("abs:href"));
				d.setUrls(urls);
				totalDocumentos.add(d);
			}

		} catch (Exception e) {
			System.out.println("Erro ao coletar a página.");
			e.printStackTrace();
		}
		return totalDocumentos;
	}

	private List<URL> listarUrls() {
		try {
			URL url = new URL("http://journals.ecs.soton.ac.uk/java/tutorial/networking/urls/readingWriting.html");
			URL url2 = new URL("https://stackoverflow.com/questions/27685839/removing-stopwords-from-a-string-in-java");
			URL url3 = new URL("http://relle.ufsc.br/moodle/mod/url/view.php?id=919");
			URL url4 = new URL(
					"https://github.com/adrielleAm/SpringBoot/blob/master/src/main/java/com/maquinadebusca/app/repository/DocumentoRepository.java");
			URL url5 = new URL("https://www.espn.com.br/nfl/index.html");

			this.urls.add(url);
			this.urls.add(url2);
			this.urls.add(url3);
			this.urls.add(url4);
			this.urls.add(url5);

		} catch (Exception e) {
			return null;
		}

		return this.urls;
	}
}