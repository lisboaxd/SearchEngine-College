package com.maquinadebusca.app.controller;

import java.net.URL;
import java.util.List;
import java.util.LinkedList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.maquinadebusca.app.model.Documento;

@RestController
@RequestMapping("/coletor") // URL: http://localhost:8080/coletor
public class Coletor {
    // URL: http://localhost:8080/coletor/iniciar
    @GetMapping(value = "/iniciar", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Documento iniciar() {
        URL url;
        Documento d = new Documento();
        try {
            url = new URL("http://journals.ecs.soton.ac.uk/java/tutorial/networking/urls/readingWriting.html");
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

            System.out.println("\n\n\n=================================================");
            System.out.println(">>> URL:");
            System.out.println("=================================================");
            System.out.println(d.getUrl());
            System.out.println("\n\n\n=================================================");
            System.out.println(">>> Página:");
            System.out.println("=================================================");
            System.out.println(d.getTexto());
            System.out.println("\n\n\n=================================================");
            System.out.println(">>> Visão:");
            System.out.println("=================================================");
            System.out.println(d.getVisao());
            System.out.println("\n\n\n=================================================");
            System.out.println(">>> URLs:");
            System.out.println("=================================================");
            urls = d.getUrls();
            for (String u : urls)
                System.out.println(u);
        } catch (Exception e) {
            System.out.println("Erro ao coletar a página.");
            e.printStackTrace();
        }
        return d;
    }
}