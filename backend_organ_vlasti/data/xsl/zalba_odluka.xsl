<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="2.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:osnova="https://github.com/draganagrbic998/xml/osnova"
xmlns:zalba="https://github.com/draganagrbic998/xml/zalba">

	<xsl:template match="/zalba:Zalba">
	
		<html>
			
			<head>
				<style>
					.root{
						max-width: 700px; 
						margin: auto; 
						border: 1px solid black; 
						padding: 50px;
						text-align: justify;
						font-family: serif;
						background-color: white;
						box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
					}
					p{
						margin: 0;
					}
					.center{
						text-align: center;
					}
					.dotted{
						border-bottom: 1px dotted black;
					}
					.bold{
						font-weight: bold;
					}
					.indent{
						text-indent: 40px;
					}
					.line{
						height: 17pt; 
						border-bottom: 1px solid black;
						word-break: break-all;
					}
					.flex{
						display: flex; 
						flex-direction: row; 
						justify-content: space-between;
					}
					.small{
						font-size: 13px;
					}
				</style>
			</head>
			
			<body>

				<div class="root">
				
					<p class="center bold small">
						ЖАЛБА  ПРОТИВ  ОДЛУКЕ ОРГАНА  ВЛАСТИ КОЈОМ ЈЕ
					</p>
	
					<p class="center bold small">
						<u>ОДБИЈЕН ИЛИ ОДБАЧЕН ЗАХТЕВ</u> ЗА ПРИСТУП ИНФОРМАЦИЈИ
					</p>
					
					<br></br>
					
					<p class="bold">
						Повереникy за информације од јавног значаја и заштиту података о личности
					</p>
	
					<p>
						Адреса за пошту:  Београд, Булевар краља Александрa бр. 15
					</p>
					
					<br></br>
					
					<p class="center bold">
						Ж А Л Б У
					</p>
					
					<br></br>
					
					<div class="flex" style="margin-left: 80px; margin-right: 80px;">
						<xsl:variable name="osoba" select="osnova:Gradjanin/osnova:Osoba"></xsl:variable>
						(<span class="center dotted" style="flex: 1;">
							<xsl:value-of select="concat($osoba/osnova:ime, concat(' ', $osoba/osnova:prezime))"></xsl:value-of>
						</span>
					</div>
					<div class="flex" style="margin-left: 100px; margin-right: 100px;">
						<xsl:variable name="adresa" select="osnova:Gradjanin/osnova:Adresa"></xsl:variable>
						<span class="center dotted" style="flex: 1;">
							<xsl:value-of select="concat($adresa/osnova:ulica, concat(' ', $adresa/osnova:broj, concat(', ', $adresa/osnova:mesto)))"></xsl:value-of>
						</span>)
					</div>
					<p class="center">
						Име, презиме, односно назив, адреса и седиште жалиоца
					</p>
					
					<br></br>
					
					<p class="center">
						против решења-закључка 
					</p>
					<div class="flex" style="margin-left: 40px; margin-right: 40px;">
						(<span class="center dotted" style="flex: 1;">
						<xsl:value-of select="osnova:OrganVlasti/osnova:naziv"></xsl:value-of>
						</span>)
					</div>
					<p class="center">
						(назив органа који је донео одлуку)
					</p>
					<p>
						<xsl:variable name="danOdluke" select="substring-after(substring-after(zalba:PodaciOdluke/osnova:datum, '-'), '-')"></xsl:variable>
						<xsl:variable name="mesecOdluke" select="substring-before(substring-after(zalba:PodaciOdluke/osnova:datum, '-'), '-')"></xsl:variable>
						<xsl:variable name="godinaOdluke" select="substring-before(zalba:PodaciOdluke/osnova:datum, '-')"></xsl:variable>
						Број 
						<span class="dotted">
							<xsl:value-of select="substring-after(zalba:PodaciOdluke/@href, 'https://github.com/draganagrbic998/xml/odluka/')"></xsl:value-of>
						</span> 
						од 
						<span class="dotted">
							<xsl:value-of select="concat($danOdluke, concat('.', concat($mesecOdluke, concat('.', concat($godinaOdluke, '.')))))"></xsl:value-of>
						</span> 
						године.
					</p>
					
					<br></br>
					
					<p class="indent">
						<xsl:variable name="danZahteva" select="substring-after(substring-after(zalba:PodaciZahteva/osnova:datum, '-'), '-')"></xsl:variable>
						<xsl:variable name="mesecZahteva" select="substring-before(substring-after(zalba:PodaciZahteva/osnova:datum, '-'), '-')"></xsl:variable>
						<xsl:variable name="godinaZahteva" select="substring-before(zalba:PodaciZahteva/osnova:datum, '-')"></xsl:variable>
						Наведеном одлуком органа власти (решењем, закључком, обавештењем у писаној форми са елементима одлуке) , 
						супротно закону, одбијен-одбачен је мој захтев који сам поднео/ла-упутио/ла дана
						<span class="dotted">
							<xsl:value-of select="concat($danZahteva, concat('.', concat($mesecZahteva, concat('.', concat($godinaZahteva, '.')))))"></xsl:value-of>
						</span>
						године и тако ми ускраћено-онемогућено остваривање уставног и законског права на 
						слободан приступ информацијама од јавног значаја. Oдлуку побијам у целости, односно у делу којим
					</p>
	
					<p>
						<p class="line">
				            <xsl:apply-templates select="osnova:Detalji"></xsl:apply-templates>
		               	</p>
						<p class="line">
						
		                </p>
						<p class="line">
						
		                </p>			
		            </p>				
					
					<p>
						јер није заснована на Закону о слободном приступу информацијама од јавног значаја.
					</p>
					<p class="indent">
						На основу изнетих разлога, предлажем да Повереник уважи моју жалбу,  поништи одлука првостепеног 
						органа и омогући ми приступ траженој/им  информацији/ма.
					</p>
					<p class="indent">
						Жалбу подносим благовремено, у законском року утврђеном у члану 22. ст. 1. 
						Закона о слободном приступу информацијама од јавног значаја.
					</p>
					
					<br></br><br></br>
	
					<div class="flex" style="align-items: center;">
						<div>
							<xsl:variable name="dan" select="substring-after(substring-after(osnova:datum, '-'), '-')"></xsl:variable>
							<xsl:variable name="mesec" select="substring-before(substring-after(osnova:datum, '-'), '-')"></xsl:variable>
							<xsl:variable name="godina" select="substring(substring-before(osnova:datum, '-'), 3, 2)"></xsl:variable>
							<p>
								У <span class="dotted">&#160;<xsl:value-of select="osnova:Gradjanin/osnova:Adresa/osnova:mesto"></xsl:value-of>&#160;</span>,
							</p>
							<p style="margin-top: 5px;">
								дана 
								<span class="dotted">
									<xsl:value-of select="concat($dan, concat('.', concat($mesec, '.')))"></xsl:value-of>
								</span> 
								20<span class="dotted">
									<xsl:value-of select="$godina"></xsl:value-of>
								</span>. године
							</p>
						</div>
						<div style="text-align: right;">
							<xsl:variable name="osoba" select="osnova:Gradjanin/osnova:Osoba"></xsl:variable>
							<xsl:variable name="adresa" select="osnova:Gradjanin/osnova:Adresa"></xsl:variable>
							<p class="dotted">
								<xsl:value-of select="concat($osoba/osnova:ime, concat(' ', $osoba/osnova:prezime))"></xsl:value-of>
							</p>
							<p>
								Подносилац жалбе / Име и презиме
							</p>
							<p class="dotted" style="margin-top: 5px;">
								<xsl:value-of select="concat($adresa/osnova:ulica, concat(' ', $adresa/osnova:broj, concat(', ', $adresa/osnova:mesto)))"></xsl:value-of>
							</p>
							<p>
								адреса
							</p>
							<p class="dotted" style="margin-top: 5px;">
								<xsl:value-of select="substring-after(@href, 'https://github.com/draganagrbic998/xml/korisnik/')"></xsl:value-of>
							</p>
							<p>
								други подаци за контакт
							</p>
							<p class="dotted" style="margin-top: 5px;">
								&#160;
							</p>
							<p>
								потпис
							</p>
	
						</div>
					</div>
					
					<br></br><br></br>
					
					<p class="bold" style="text-indent: 20px;">
						Напомена:
					</p>
					<ul style="margin-top: 0; padding-left: 30px;">
						<li>
							У жалби се мора навести одлука која се побија (решење, закључак, обавештење), назив органа 
							који је одлуку донео, као и број и датум одлуке. Довољно је да жалилац наведе у жалби 
							у ком погледу је незадовољан одлуком, с тим да жалбу не мора посебно образложити. 
							Ако жалбу изјављује на овом обрасцу, додатно образложење може  посебно приложити.					
						</li>
						<li>
							<xsl:variable name="zahtev_link" select="concat('http://localhost:4200/html/zahtevi/', substring-after(zalba:PodaciZahteva/@href, 'https://github.com/draganagrbic998/xml/zahtev/'))"></xsl:variable>
							<xsl:variable name="odluka_link" select="concat('http://localhost:4200/html/odluke/', substring-after(zalba:PodaciOdluke/@href, 'https://github.com/draganagrbic998/xml/odluka/'))"></xsl:variable>
							Уз жалбу обавезно приложити <a href="{$zahtev_link}">копију поднетог захтева</a> и доказ о његовој предаји-упућивању 
							органу као и <a href="{$odluka_link}">копију одлуке органа</a> која се оспорава жалбом.					
						</li>
					</ul>
					
				
				</div>
			
			</body>
		
		</html>
	
	</xsl:template>

    <xsl:template match="osnova:bold">
        <b><xsl:apply-templates select="@*|node()"></xsl:apply-templates></b>
    </xsl:template>
    
    <xsl:template match="osnova:italic">
        <i><xsl:apply-templates select="@*|node()"></xsl:apply-templates></i>
    </xsl:template>

</xsl:stylesheet>