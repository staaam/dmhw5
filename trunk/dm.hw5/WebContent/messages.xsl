<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/">
  <html>
  <head>
  	<link href="messages.css" rel="stylesheet" type="text/css" />
  </head>
  <body>
  <div class="msgs">
      <xsl:for-each select="messages/message_list">
      <div class="msg-list">
      	<div class="type"><span class="label">Type: </span><xsl:value-of select="type"/></div>
        <xsl:for-each select="message">
          <div class="msg">
            <div class="title"><span class="label">Title: </span><xsl:value-of select="title"/></div>
            <div class="details">
              <div class="rank"><span class="label">Rank: </span><xsl:value-of select="rank"/></div>
              <div class="start"><span class="label">Start: </span><xsl:value-of select="start"/></div>
              <div class="end"><span class="label">End: </span><xsl:value-of select="end"/></div>
            </div>
            <div class="body"><span class="label">Body: </span><xsl:value-of select="body"/></div>
            <xsl:if test="id">
              <div class="id">
              	<xsl:element name="a">
              		<xsl:attribute name="class">id</xsl:attribute>
              		<xsl:attribute name="href">deletemessage?msgid=<xsl:value-of select="id"/></xsl:attribute>
              		Delete
             	</xsl:element>
               </div>
            </xsl:if>
          </div>
        </xsl:for-each>
      </div>
      </xsl:for-each>
  </div>
  </body>

  </html>
</xsl:template>
</xsl:stylesheet>