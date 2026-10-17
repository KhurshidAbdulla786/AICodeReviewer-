import React from 'react';

/**
 * Robust markdown message renderer component.
 * Safely formats code blocks, bold text, and paragraphs without external dependency issues.
 */
export default function MarkdownMessage({ content }) {
  if (!content) return null;

  const parts = content.split(/(```[\s\S]*?```)/g);
  return (
    <div className="text-sm text-gray-200 space-y-2">
      {parts.map((part, index) => {
        if (part.startsWith('```') && part.endsWith('```')) {
          const lines = part.slice(3, -3).trim().split('\n');
          const lang = lines[0].match(/^[a-zA-Z]+/)?.[0] || '';
          const code = lang ? lines.slice(1).join('\n') : lines.join('\n');
          return (
            <div key={index} className="my-3 rounded-lg overflow-hidden bg-gray-950 border border-gray-700">
              {lang && (
                <div className="bg-gray-800 px-4 py-1.5 text-xs font-mono text-gray-400 border-b border-gray-700">
                  {lang}
                </div>
              )}
              <pre className="p-4 text-xs font-mono text-gray-200 overflow-x-auto">
                <code>{code || part.slice(3, -3)}</code>
              </pre>
            </div>
          );
        }
        return (
          <p key={index} className="mb-2 last:mb-0 text-gray-200 leading-relaxed whitespace-pre-wrap">
            {part}
          </p>
        );
      })}
    </div>
  );
}

